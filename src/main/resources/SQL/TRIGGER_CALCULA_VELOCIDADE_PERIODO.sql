CREATE OR ALTER TRIGGER trg_calculate_wave_params
ON simulacoes
AFTER INSERT
AS
BEGIN
    -- Atualiza as colunas de velocidade e per�odo, com verifica��o de frequ�ncia > 0
    UPDATE s
    SET s.velocidade = i.frequencia * i.comprimento_onda,
        s.periodo =  1 / i.frequencia
    FROM simulacoes s
    INNER JOIN inserted i ON s.id = i.id
END;
