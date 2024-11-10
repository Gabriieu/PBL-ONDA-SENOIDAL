CREATE OR ALTER PROCEDURE SPINSEREUSUARIO(
	@Name VARCHAR(50),
	@Password VARCHAR(200),
	@Role VARCHAR(25),
	@Email VARCHAR(100)
) AS
BEGIN
	INSERT INTO 
		usuarios
		(name, password, role, email)
	VALUES
		(@Name, @Password, @Role, @Email)
END