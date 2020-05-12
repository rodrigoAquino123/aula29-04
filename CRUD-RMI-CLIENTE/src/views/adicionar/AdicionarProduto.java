package views.adicionar;

import interfaces.InterfaceProduto;
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

public class AdicionarProduto extends JPanel implements ActionListener {

    private final JTextField campo_nome;
    private final JTextField campo_preco;
    private final JTextField campo_quantidade;

    public AdicionarProduto(JButton botaoAdicionar) {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new GridLayout(6, 0));
        JLabel label_nome = new JLabel("Nome: ");
        campo_nome = new JTextField(20);
        JLabel label_preco = new JLabel("Preço: ");
        campo_preco = new JTextField(20);
        JLabel label_quantidade = new JLabel("Quantidade: ");
        campo_quantidade = new JTextField(20);

        botaoAdicionar.addActionListener(this);

        add(label_nome);
        add(campo_nome);
        add(label_preco);
        add(campo_preco);
        add(label_quantidade);
        add(campo_quantidade);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        String descricao = campo_nome.getText();
        double preco = Double.parseDouble(campo_preco.getText());
        int quantidade = Integer.parseInt(campo_quantidade.getText());

        try {
            InterfaceProduto produtoRemoto = (InterfaceProduto) Naming.lookup("rmi://" + IP_SERVIDOR + ":1099/Produto");

            produtoRemoto.setDescricao(descricao);
            produtoRemoto.setPreco(preco);
            produtoRemoto.setQuantidade(quantidade);

            produtoRemoto.adicionar();

            String texto_retorno = "\nDescrição: " + produtoRemoto.getDescricao() + "\nPreço: "
                    + produtoRemoto.getPreco() + "\nQuantidade: " + produtoRemoto.getQuantidade();

            JOptionPane.showMessageDialog(null, texto_retorno, "Dados do Produto", JOptionPane.INFORMATION_MESSAGE);
        } catch (RemoteException re) {
            JOptionPane.showMessageDialog(null, "Erro remoto: " + re.toString(), "Erro remoto",
                    JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro local: " + e.toString(), "Erro local",
                    JOptionPane.WARNING_MESSAGE);
        }

    }

}
