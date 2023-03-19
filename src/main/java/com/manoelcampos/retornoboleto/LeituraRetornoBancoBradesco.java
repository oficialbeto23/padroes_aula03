package com.manoelcampos.retornoboleto;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeituraRetornoBancoBradesco implements LeituraRetorno {
    @Override
    public List<Boleto> lerArquivo(final URI caminhoArquivo) {
        try (var reader = Files.newBufferedReader(Paths.get(caminhoArquivo))){
            String line;
            final var listaBoletos = new ArrayList<Boleto>();
            while((line = reader.readLine()) != null){
                final String[] vetor = line.split(";");
                final var boleto = new Boleto();
                boleto.setId(Integer.parseInt(vetor[0]));
                boleto.setCodBanco(vetor[1]);
                // end::class-start[]

                //Bradesco:
                boleto.setAgencia(vetor[2]);//3
                boleto.setContaBancaria(vetor[3]);//4

                boleto.setDataVencimento(LocalDate.parse(vetor[4], FORMATO_DATA));
                boleto.setDataPagamento(LocalDate.parse(vetor[5], FORMATO_DATA_HORA).atTime(0, 0, 0));

                boleto.setCpfCliente(vetor[6]);
                boleto.setValor(Double.parseDouble(vetor[7]));
                boleto.setMulta(Double.parseDouble(vetor[8]));
                boleto.setJuros(Double.parseDouble(vetor[9]));

                // tag::class-end[]
                listaBoletos.add(boleto);
            }

            return listaBoletos;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
// end::class-end[]