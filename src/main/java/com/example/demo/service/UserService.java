package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exception.exceptions.EntityNotFoundException;
import com.example.demo.exception.exceptions.InvalidPasswordException;
import com.example.demo.exception.exceptions.UsernameUniqueViolationException;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (
                DataIntegrityViolationException exception) {
            throw new UsernameUniqueViolationException("Email já cadastrado");
        }
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    @Transactional(readOnly = true)
    public User.Role findRoleByUsername(String username) {
        return userRepository.findRoleByUsername(username);
    }

    @Transactional
    public void updatePassword(Long id, String password, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new InvalidPasswordException("Senhas não conferem");
        }

        User user = findById(id);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("Senha inválida");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
    }

    @Transactional
    public void updateName(Long id, String newName) {
        User user = findById(id);
        user.setName(newName.toUpperCase());
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable page) {
        return userRepository.findAll(page);
    }

    @Transactional
    public void delete(Long id, String password) {
        boolean passwordMatch = passwordEncoder.matches(password, findById(id).getPassword());

        if(!passwordMatch) {
            throw new InvalidPasswordException("Senha inválida");
        }

        userRepository.deleteById(id);
    }
}
