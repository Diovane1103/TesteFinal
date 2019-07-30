package com.Diovane1103.coreengineering.temaFinal.Persistencia;

import com.Diovane1103.coreengineering.temaFinal.Excecao.IdentificadorInvalidoException;

import java.io.IOException;
import java.nio.file.*;

public class GerenciadorPasta {
    private static final String CAMINHO_PASTA = "/data/in";
    private static final ArquivosDAO arquivoAcoes = new ArquivosDAO();

    public void iniciaGerenciamento() throws Throwable {
        Path path = Paths.get(System.getProperty("user.home") + CAMINHO_PASTA);
        WatchService watchService;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            path.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

        } catch(IOException io) {
            throw new IOException(io.getMessage());
        }
        WatchKey key;
        while((key = watchService.take()) != null) {
            this.carregaConteudoDiretorio(path);
            try {
                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.context().toString().contains(".dat")) {
                        WatchEvent.Kind<?> kind = event.kind();
                        switch (kind.toString()) {
                            case "ENTRY_CREATE":
                                arquivoAcoes.lerArquivo(event.context().toString());
                                break;
                            case "ENTRY_MODIFY":
                                arquivoAcoes.lerArquivo(event.context().toString().substring(0, event.context().toString().indexOf("_")));
                                break;
                            default:
                                throw new IdentificadorInvalidoException("Evento " + kind.toString() + " não é tratável pelo sistema.");
                        }
                }
                }
            } finally {
                boolean reset = key.reset();
                if(!reset){
                    break;
                }
            }
        }
        arquivoAcoes.escreverArquivo();
    }

    public void carregaConteudoDiretorio(Path caminho) throws Throwable {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(caminho, "*.dat")) {
            for (Path arquivo: stream) {
                arquivoAcoes.lerArquivo(arquivo.getFileName().toString());
            }
        } catch (DirectoryIteratorException | IOException ex) {
            throw ex.getCause();
        }
    }

}
