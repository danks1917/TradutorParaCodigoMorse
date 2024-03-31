
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// KeyListener é usado aqui para conseguirmos ouvir o pressionamento das teclas (digitando)
public class TradutorCodigoMorseGUI extends JFrame implements KeyListener {
    private ControladorCodigoMorse controladorCodigoMorse;


    // areaInputTexto - input do usuario (texto para ser traduzido)
    // areaCodigoMorse - texto traduzido em codigo morse
    private JTextArea areaInputTexto, areaCodigoMorse;

    public TradutorCodigoMorseGUI(){
        // Adiciona texto para a barra do titulo
        super("Tradutor Codigo Morse");

        // Atribui o valor do frame para 540x760 pixels
        setSize(new Dimension(540, 760));

        // Previne o GUI de poder alterar o tamanho
        setResizable(false);

        // Setar o layout como nulo nos permite posicionar e definir manualmente o tamanho dos componentes em nosso GUI
        setLayout(null);

        // Sai do programa ao fechar o GUI
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Troca a cor do fundo
        getContentPane().setBackground(Color.decode("#264653"));

        // Posiciona o GUI no centro da tela quando "ran"
        setLocationRelativeTo(null);

        controladorCodigoMorse = new ControladorCodigoMorse();
        componentesGUI();

    }
    
    private void componentesGUI(){
        // Label do titulo
        JLabel tituloLabel = new JLabel("Tradutor Código Morse");

        // Altera o tamanho e a espessura da fonte
        tituloLabel.setFont(new Font("Dialog", Font.BOLD, 32));

        // Altera a cor do texto do titulo para branco 
        tituloLabel.setForeground(Color.WHITE);

        // Centraliza o texto
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Seta as posiçoes do x, y e as dimensões de largura e altura
        // Para garantir que o titulo alinhe com o centro do GUI, colocamos a mesma largura
        tituloLabel.setBounds(0, 0, 540, 100);

        ////////////////////////////////////////////////////////////////////////////////////

        // Input do Texto
        JLabel inputTextoLabel = new JLabel("Texto:");
        inputTextoLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        inputTextoLabel.setForeground(Color.WHITE);
        inputTextoLabel.setBounds(20, 100, 200, 30);

        areaInputTexto = new JTextArea();
        areaInputTexto.setFont(new Font("Dialog", Font.PLAIN, 18));

        // Faz com que ouçamos a digitaçao na area de texto
        areaInputTexto.addKeyListener(this);
        
        // Simula preenchimento de 10px na area de texto
        areaInputTexto.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Faz com que as palavras movam para a linha de baixo ao atingir o final da area de texto
        areaInputTexto.setLineWrap(true);

        // Faz  com que quando as palavras forem quebradasa, a palavra não se separe
        areaInputTexto.setWrapStyleWord(true);

        // Adiciona a capacidade de scroll na area de texto
        JScrollPane scrollInputTexto = new JScrollPane(areaInputTexto);
        scrollInputTexto.setBounds(20, 132, 484, 236);

        ////////////////////////////////////////////////////////////////////////////////////
        
        // Input do CodigoMorse
        JLabel inputCodigMorse = new JLabel("Codigo Morse:");
        inputCodigMorse.setFont(new Font("Dialog", Font.PLAIN, 16));
        inputCodigMorse	.setForeground(Color.WHITE);
        inputCodigMorse.setBounds(20, 390, 200, 30);

        areaCodigoMorse = new JTextArea();
        areaCodigoMorse.setFont(new Font("Dialog", Font.PLAIN, 18));
        areaCodigoMorse.setEditable(false);
        areaCodigoMorse.setLineWrap(true);
        areaCodigoMorse.setWrapStyleWord(true);
        areaCodigoMorse.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Adiciona a capacidade de scroll na area do codigo morse
        JScrollPane scrollCodigoMorse = new JScrollPane(areaCodigoMorse);
        scrollCodigoMorse.setBounds(20, 430, 484, 236);


        // Toca o som do botao
        JButton tocarSomBotao = new JButton("Tocar som");
        tocarSomBotao.setBounds(210, 680, 100, 30);
        tocarSomBotao.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                // desliga o tocar botao
                tocarSomBotao.setEnabled(false);

                Thread tocarCodigoMorseThread = new Thread(new Runnable() {
                    @Override
                    public void run(){
                        try{
                            String[] mensagemMorse = areaCodigoMorse.getText().split(" ");
                            controladorCodigoMorse.tocarSom(mensagemMorse);
                        }catch(LineUnavailableException lineUnavailableException){
                            lineUnavailableException.printStackTrace();
                        }catch(InterruptedException InterruptedException){
                            InterruptedException.printStackTrace();
                        }finally{
                            // "enable" tocar botao
                            tocarSomBotao.setEnabled(true);
                        }
                    }
                });
                tocarCodigoMorseThread.start();
            }
        });

        // Adiciona ao GUI
        add(tituloLabel);
        add(inputTextoLabel);
        add(scrollInputTexto);
        add(inputCodigMorse);
        add(scrollCodigoMorse);
        add(tocarSomBotao);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Ignora o SHIFT
        if(e.getKeyCode() != KeyEvent.VK_SHIFT){
            // Recupera o input 
            String inputTexto = areaInputTexto.getText();

            // Atualiza o GUI com o texto traduzido
            areaCodigoMorse.setText(controladorCodigoMorse.traduzParaMorse(inputTexto));
        }
    }
}
