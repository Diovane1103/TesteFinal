package com.Diovane1103.coreengineering.temaFinal.Negocio;

import java.util.ArrayList;
import java.util.List;

public class Venda {

    private int id;
    private static List<ItemVendido> itensVenda = new ArrayList<>();
    private String nomeVendedor;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public List<ItemVendido> getItensVenda() { return itensVenda; }

    public String getNomeVendedor() { return nomeVendedor; }

    public void setNomeVendedor(String nomeVendedor) { this.nomeVendedor = nomeVendedor; }

    public Double valorVenda() { return getItensVenda().stream().map(ItemVendido::getValor).reduce((a,b) -> a * b).get(); }
}
