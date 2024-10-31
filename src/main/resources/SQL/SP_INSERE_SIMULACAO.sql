CREATE OR ALTER PROCEDURE SPINSERESIMULACAO
    @frequencia FLOAT,
    @comprimentoOnda FLOAT,
    @duracao INT,
    @erroMax FLOAT,
    @SimulacaoID BIGINT OUTPUT
AS
BEGIN
    -- Inserir a simula��o com arredondamento do per�odo
    INSERT INTO simulacoes (frequencia, comprimento_onda, duracao, erro_max, data)
    VALUES (@frequencia, @comprimentoOnda, @duracao, @erroMax, GETDATE());

    -- Retornar o ID gerado
    SET @SimulacaoID = SCOPE_IDENTITY();
END;
