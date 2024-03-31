// Esse codigo vai lidar com a logica do nosso GUI

import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
public class ControladorCodigoMorse {
     // usaremos um hashmap para traduzir a entrada do usuário em código morse
     // um hashmap é uma estrutura de dados que armazena pares chave/valor
     // neste caso, usaremos a letra como chave e o código morse correspondente como valor
     // desta forma, podemos facilmente procurar o código Morse para qualquer letra usando a letra como chave

     // aqui estou declarando que um hashmap tem uma chave do tipo "Character" com um valor do tipo "String"
     private HashMap<Character, String> mapCodigoMorse;

    public ControladorCodigoMorse(){

        mapCodigoMorse = new HashMap<>();

        // uppercase
        mapCodigoMorse.put('A', ".-");
        mapCodigoMorse.put('B', "-...");
        mapCodigoMorse.put('C', "-.-.");
        mapCodigoMorse.put('D', "-..");
        mapCodigoMorse.put('E', ".");
        mapCodigoMorse.put('F', "..-.");
        mapCodigoMorse.put('G', "--.");
        mapCodigoMorse.put('H', "....");
        mapCodigoMorse.put('I', "..");
        mapCodigoMorse.put('J', ".---");
        mapCodigoMorse.put('K', "-.-");
        mapCodigoMorse.put('L', ".-..");
        mapCodigoMorse.put('M', "--");
        mapCodigoMorse.put('N', "-.");
        mapCodigoMorse.put('O', "---");
        mapCodigoMorse.put('P', ".--.");
        mapCodigoMorse.put('Q', "--.-");
        mapCodigoMorse.put('R', ".-.");
        mapCodigoMorse.put('S', "...");
        mapCodigoMorse.put('T', "-");
        mapCodigoMorse.put('U', "..-");
        mapCodigoMorse.put('V', "...-");
        mapCodigoMorse.put('W', ".--");
        mapCodigoMorse.put('X', "-..-");
        mapCodigoMorse.put('Y', "-.--");
        mapCodigoMorse.put('Z', "--..");

        // lowercase
        mapCodigoMorse.put('a', ".-");
        mapCodigoMorse.put('b', "-...");
        mapCodigoMorse.put('c', "-.-.");
        mapCodigoMorse.put('d', "-..");
        mapCodigoMorse.put('e', ".");
        mapCodigoMorse.put('f', "..-.");
        mapCodigoMorse.put('g', "--.");
        mapCodigoMorse.put('h', "....");
        mapCodigoMorse.put('i', "..");
        mapCodigoMorse.put('j', ".---");
        mapCodigoMorse.put('k', "-.-");
        mapCodigoMorse.put('l', ".-..");
        mapCodigoMorse.put('m', "--");
        mapCodigoMorse.put('n', "-.");
        mapCodigoMorse.put('o', "---");
        mapCodigoMorse.put('p', ".--.");
        mapCodigoMorse.put('q', "--.-");
        mapCodigoMorse.put('r', ".-.");
        mapCodigoMorse.put('s', "...");
        mapCodigoMorse.put('t', "-");
        mapCodigoMorse.put('u', "..-");
        mapCodigoMorse.put('v', "...-");
        mapCodigoMorse.put('w', ".--");
        mapCodigoMorse.put('x', "-..-");
        mapCodigoMorse.put('y', "-.--");
        mapCodigoMorse.put('z', "--..");

        // numbers
        mapCodigoMorse.put('0', "-----");
        mapCodigoMorse.put('1', ".----");
        mapCodigoMorse.put('2', "..---");
        mapCodigoMorse.put('3', "...--");
        mapCodigoMorse.put('4', "....-");
        mapCodigoMorse.put('5', ".....");
        mapCodigoMorse.put('6', "-....");
        mapCodigoMorse.put('7', "--...");
        mapCodigoMorse.put('8', "---..");
        mapCodigoMorse.put('9', "----.");

        // special characters
        mapCodigoMorse.put(' ', "/");
        mapCodigoMorse.put(',', "--..--");
        mapCodigoMorse.put('.', ".-.-.-");
        mapCodigoMorse.put('?', "..--..");
        mapCodigoMorse.put(';', "-.-.-.");
        mapCodigoMorse.put(':', "---...");
        mapCodigoMorse.put('(', "-.--.");
        mapCodigoMorse.put(')', "-.--.-");
        mapCodigoMorse.put('[', "-.--.");
        mapCodigoMorse.put(']', "-.--.-");
        mapCodigoMorse.put('{', "-.--.");
        mapCodigoMorse.put('}', "-.--.-");
        mapCodigoMorse.put('+', ".-.-.");
        mapCodigoMorse.put('-', "-....-");
        mapCodigoMorse.put('_', "..--.-");
        mapCodigoMorse.put('"', ".-..-.");
        mapCodigoMorse.put('\'', ".----.");
        mapCodigoMorse.put('/', "-..-.");
        mapCodigoMorse.put('\\', "-..-.");
        mapCodigoMorse.put('@', ".--.-.");
        mapCodigoMorse.put('=', "-...-");
        mapCodigoMorse.put('!', "-.-.--");

    }

    public String traduzParaMorse(String textoParaTraduzir){
        StringBuilder textoTraduzido = new StringBuilder();
        for(Character letra : textoParaTraduzir.toCharArray()){
            // traduz a letra e depois acrescenta ao valor retornado (para ser exibido na GUI)
            textoTraduzido.append(mapCodigoMorse.get(letra) + " " );

        }
        return textoTraduzido.toString();
    }

    // mensagemMorse - contém a mensagem em codigo morse apos ser traduzida 
    public void tocarSom(String[] mensagemMorse) throws InterruptedException, LineUnavailableException{
        // Especifica as propriedades do audio
        AudioFormat audioFormat = new AudioFormat(44100, 16, 1, true, false);

        // Cria a data line 
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
        SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();

        // Duraçao dos sons de cada digito do codigo morse (., -, /)
        int pontoDuracao = 200;
        int linhaDuracao = (int) (1.5 * pontoDuracao);
        int barraDuracao = 2 * linhaDuracao;

        for(String pattern : mensagemMorse){
            // Toca o som da letra
            for(char c : pattern.toCharArray()){
                if(c == '.'){
                    tocarBeep(sourceDataLine, pontoDuracao);
                    Thread.sleep(pontoDuracao);
                }else if (c == '-'){
                    tocarBeep(sourceDataLine, linhaDuracao);
                    Thread.sleep(pontoDuracao);
                }else if (c == '/'){
                    Thread.sleep(barraDuracao);
                }
            }

            // Espera um pouco antes de tocar a proxima sequencia 
            Thread.sleep(pontoDuracao);

        }

        //  Fecha os audios("clean" nos recursos)
        sourceDataLine.drain();
        sourceDataLine.stop();
        sourceDataLine.close();
    }

    private void tocarBeep(SourceDataLine line, int duracao){
        byte[] data = new byte[duracao * 44100 / 1000];

        for(int i = 0; i < data.length; i++){
            // calcula o ângulo da onda senoidal para a amostra atual com base na taxa de amostragem e frequência
            double angulo = i / (44100.0/440) * 2 * Math.PI;

            // calcula a amplitude da onda senoidal no ângulo atual e a dimensiona para caber dentro da faixa de
            // um byte assinado (-128, 127)
            // também no contexto do processamento de áudio, bytes assinados são frequentemente usados para representar dados de áudio porque
            // pode representar amplitudes positivas e negativas de ondas sonoras
            data[i] = (byte) (Math.sin(angulo) * 127.0);
        }

        line.write(data, 0, data.length);
    }

}
