package com.batch.app01;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing // para trazer todas as features do spring batch, é necessário habilita-lo
@Configuration // necessário ser uma classe de configuração
public class BatchConfig {
 
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    // informa que um objeto existe para o contexto, pra ser injetado
    @Bean // retorna um job
    public Job imprimeOlaJob() {
        return jobBuilderFactory
                .get("imprimeOlaJob")// nome do job
                /**
                 * qual a logica do job, o job é dividido em etapas que são chamado de steps,
                 * steps são encadeados para contér um lógica maior
                 */
                .start(imprimeOlaStep())
                .build(); // construção do job
    }
    
    public Step imprimeOlaStep() {
        return stepBuilderFactory
                .get("imprimeOlaStep") // todos começam com get, que é o nome do step
                .tasklet(new Tasklet() { // pequenas tarefas, que necessitam de pouco poder no processamento
                    
                    @Override
                    public RepeatStatus execute(StepContribution contribution,
                            ChunkContext chunkContext) throws Exception {
                        System.out.println("Olá mundo");
                        System.out.println("Executa a lógica");
                        return RepeatStatus.FINISHED; // indica que a tarefa terminou
                    }
                }).build();
    }
}
