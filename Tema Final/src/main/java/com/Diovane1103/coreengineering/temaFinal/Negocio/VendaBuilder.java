package com.Diovane1103.coreengineering.temaFinal.Negocio;

public class VendaBuilder {

    private Venda venda;

    public VendaBuilder() {
        this.venda = new Venda();
    }

    public static VendaBuilder builder() {
        return new VendaBuilder();
    }

    public VendaBuilder addId(int id) {
        this.venda.setId(id);
        return this;
    }

    public VendaBuilder addNomeVendedor(String nomeVendedor) {
        this.venda.setNomeVendedor(nomeVendedor);
        return this;
    }

    public VendaBuilder addItensVendidos(String[] itens) {
        ItemVendido itemVendido = null;
        for (int i = 0; i < itens.length; i++) {
            String [] dados = itens[i].split("-");
            for (int j = 0; j < dados.length; j++) {
                itemVendido = new ItemVendido();
                itemVendido.setId(Integer.parseInt(dados[0]));
                itemVendido.setQuantidade(Integer.parseInt(dados[1]));
                itemVendido.setValor(Double.parseDouble(dados[2]));
                this.venda.getItensVenda().add(itemVendido);
            }
        }
        return this;
    }

    public Venda terminar() {
        return this.venda;
    }
}
