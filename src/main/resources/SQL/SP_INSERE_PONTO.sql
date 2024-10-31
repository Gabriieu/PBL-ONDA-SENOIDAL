CREATE OR ALTER PROCEDURE SPINSEREPONTO
    @wave_id INT,
    @x FLOAT,
    @y FLOAT,
    @t FLOAT
AS
BEGIN
    SET NOCOUNT ON;
    INSERT INTO pontos (wave_id, x, y, t)
    VALUES (@wave_id, @x, @y, @t);
END;
