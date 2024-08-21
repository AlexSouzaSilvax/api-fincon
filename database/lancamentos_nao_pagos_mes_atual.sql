CREATE EXTENSION IF NOT EXISTS pg_cron;

SELECT cron.schedule(
    'ultimo_dia_mes_job',
    '59 23 28-31 * *',  
    $$SELECT lancamentos_nao_pago_mes_atual();$$
);

CREATE OR REPLACE FUNCTION lancamentos_nao_pago_mes_atual()
RETURNS VOID AS $$
DECLARE
    mes_atual INTEGER := EXTRACT(MONTH FROM CURRENT_DATE);
    ano_atual INTEGER := EXTRACT(YEAR FROM CURRENT_DATE);
    nome_mes_atual TEXT;
    nome_mes_seguinte TEXT;
    lancamento RECORD;
BEGIN
     -- Verifica se hoje é o último dia do mês
    IF (CURRENT_DATE + INTERVAL '1 day')::DATE = date_trunc('month', CURRENT_DATE) + INTERVAL '1 month' THEN
    
    -- Array com os nomes dos meses por extenso
    nome_mes_atual := ARRAY['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];
    nome_mes_seguinte := ARRAY['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];

    FOR lancamento IN
        SELECT *
        FROM lancamento
        WHERE EXTRACT(MONTH FROM data) = mes_atual
          AND EXTRACT(YEAR FROM data) = ano_atual
          AND pago = FALSE
    LOOP
        INSERT INTO lancamento(data, valor, pago, observacao)
        VALUES (
            lancamento.data + INTERVAL '1 month',  -- Incrementa um mês
            lancamento.valor,
            FALSE,
            'Lançamento não pago do mês ' || nome_mes_atual || ' de ' || ano_atual
        );
    END LOOP;
    END IF;
END;
$$ LANGUAGE plpgsql;

select lancamentos_nao_pago_mes_atual();

select * from lancamento;
/*
id, ano_referencia, categoria, data_lancamento, data_pagamento, data_prevista_pagamento, 
data_vencimento, descricao, mensal, mes_referenrencia, numero_parcela, observacao, pago, 
quantidade_parcelas, tipo_lancamento, valor, id_usuario
*/