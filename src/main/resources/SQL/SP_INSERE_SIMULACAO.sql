CREATE OR ALTER PROCEDURE SPINSERESIMULACAO
    @frequencia FLOAT,
    @comprimentoOnda FLOAT,
    @duracao INT,
    @erroMax FLOAT,
	@user BIGINT,
    @SimulacaoID BIGINT OUTPUT
AS
BEGIN
    -- Inserir a simulação com arredondamento do período
    INSERT INTO simulacoes (frequencia, comprimento_onda, duracao, erro_max, data, user_id)
    VALUES (@frequencia, @comprimentoOnda, @duracao, @erroMax, GETDATE(), @user);

    -- Retornar o ID gerado
    SET @SimulacaoID = SCOPE_IDENTITY();
END;
