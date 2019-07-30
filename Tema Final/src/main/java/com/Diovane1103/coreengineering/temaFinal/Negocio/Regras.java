package com.Diovane1103.coreengineering.temaFinal.Negocio;

import com.Diovane1103.coreengineering.temaFinal.Persistencia.ArquivosDAO;
import java.util.Comparator;
import java.util.List;

public class Regras {

    private static ArquivosDAO arquivosDAO = new ArquivosDAO();

    public long qtdClientes() { return arquivosDAO.getClientes().stream().count(); }

    public long qtdVendedores() { return arquivosDAO.getVendedores().stream().count(); }

    public int maiorVenda() { return minMaxVendas(MinMaxVenda.MaxVenda).getId(); }

    public Vendedor piorVendedor() {
        return arquivosDAO.getVendedores().stream().filter(vendedor -> vendedor.getNome().equals(minMaxVendas(MinMaxVenda.MinVenda).getNomeVendedor())).findAny().orElse(null);
    }

    private Venda minMaxVendas(MinMaxVenda tipo) {
        List<Venda> vendasAux = arquivosDAO.getVendas();
        if(tipo.equals("+")){
            return vendasAux.stream().max(Comparator.comparing(Venda::valorVenda)).get();
        } else {
            return vendasAux.stream().min(Comparator.comparing(Venda::valorVenda)).get();
        }
    }

}
