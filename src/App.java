import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {

        // "Invocar" posteriormente garante que o GUI seja criado e atualizado de maneira segura para threads
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
                new TradutorCodigoMorseGUI().setVisible(true);
            }
        });
    }
}
