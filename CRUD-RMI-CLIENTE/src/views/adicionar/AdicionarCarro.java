package views.adicionar;

import interfaces.InterfaceCarro;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import static principal.Consts.IP_SERVIDOR;

public class AdicionarCarro extends JPanel implements ActionListener {

    private final JTextField campo_modelo;
    private final JTextField campo_ano;
    private final JTextField campo_nota;

    public AdicionarCarro(JButton botaoAdicionar) {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new GridLayout(6, 0));
        JLabel label_modelo = new JLabel("Modelo: ");
        campo_modelo = new JTextField(20);
        JLabel label_ano = new JLabel("Ano: ");
        campo_ano = new JTextField(20);
        JLabel label_nota = new JLabel("Nota: ");
        campo_nota = new JTextField(20);

        botaoAdicionar.addActionListener(this);

        add(label_modelo);
        add(campo_modelo);
        add(label_ano);
        add(campo_ano);
        add(label_nota);
        add(campo_nota);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        String modelo = campo_modelo.getText();
        int ano = Integer.parseInt(campo_ano.getText());
        double nota = Double.parseDouble(campo_nota.getText());

        try {
            InterfaceCarro carroRemoto = (InterfaceCarro) Naming.lookup("rmi://" + IP_SERVIDOR + ":1099/Carro");

            carroRemoto.setModelo(modelo);
            carroRemoto.setAno(ano);
            carroRemoto.setNota(nota);

            carroRemoto.adicionar();

            String texto_retorno = "\nModelo: " + carroRemoto.getModelo() + "\nAno: "
                    + carroRemoto.getAno() + "\nNota: " + carroRemoto.getNota();

            JOptionPane.showMessageDialog(null, texto_retorno, "Dados do Carro", JOptionPane.INFORMATION_MESSAGE);
        } catch (RemoteException re) {
            JOptionPane.showMessageDialog(null, "Erro remoto: " + re.toString(), "Erro remoto",
                    JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro local: " + e.toString(), "Erro local",
                    JOptionPane.WARNING_MESSAGE);
        }

    }

}
