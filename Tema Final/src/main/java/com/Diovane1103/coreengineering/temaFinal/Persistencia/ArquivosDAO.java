package com.Diovane1103.coreengineering.temaFinal.Persistencia;

import com.Diovane1103.coreengineering.temaFinal.Excecao.IdentificadorInvalidoException;
import com.Diovane1103.coreengineering.temaFinal.Negocio.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivosDAO {
    private static final String CAMINHO_OUTPUT = System.getProperty("user.home") + "/data/out/Output.done.dat";
    private static final String CAMINHO_INPUT = System.getProperty("user.home") + "/data/in/";

    private static Regras regras = new Regras();

    private static List<Vendedor> vendedores = new ArrayList<>();
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Venda> vendas = new ArrayList<>();

    public void lerArquivo(String fileName) throws IOException {
        try(FileReader fileReader = new FileReader(CAMINHO_INPUT + fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                tratarDados(linha);
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public void escreverArquivo() throws IOException {
        try(FileWriter fileWriter = new FileWriter(CAMINHO_OUTPUT)) {
            fileWriter.write("Quantidade de Clientes no arquivo de entrada: " + regras.qtdClientes() + "\r\n"
                               + "Quantidade de Vendedores no arquivo de entrada: " + regras.qtdVendedores() + "\r\n"
                               + "Id da maior venda: " + regras.maiorVenda() + "\r\n"
                               + "Pior vendedor: " + regras.piorVendedor());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public void tratarDados(String linha) {
        if (linha != "") {
            String delimitador = delimitadorPadrao(linha);
            String[] dados = linha.split(delimitador);
            switch (dados[0]) {
                case "001":
                    popularVendedor(dados[1], dados[2], Double.parseDouble(dados[3]));
                    break;
                case "002":
                    popularCliente(dados[1], dados[2], dados[3]);
                    break;
                case "003":
                    popularVenda(Integer.parseInt(dados[1]), dados[2].replace("[", "").replace("]", "").split(","), dados[3]);
                    break;
                default:
                    throw new IdentificadorInvalidoException("O identificador encontrado não é aceito pelo sistema!");
            }
        }
    }

    public List<Cliente> getClientes() { return clientes; }

    public List<Venda> getVendas() { return vendas; }

    public List<Vendedor> getVendedores() { return vendedores; }

    private void popularVendedor(String cpf, String nome, Double salario) { this.vendedores.add(new Vendedor(cpf, nome, salario)); }

    private void popularCliente(String cnpj, String nome, String areaNegocio) { this.clientes.add(new Cliente(cnpj, nome, areaNegocio)); }

    private void popularVenda(int id, String[] itens, String nomeVendedor) { this.vendas.add(VendaBuilder.builder().addId(id).addItensVendidos(itens).addNomeVendedor(nomeVendedor).terminar()); }

    private String delimitadorPadrao(String linha) { return linha.substring(3,4); }
}
