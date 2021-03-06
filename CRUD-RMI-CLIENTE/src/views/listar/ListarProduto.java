package views.listar;

import interfaces.InterfaceProduto;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static principal.Consts.IP_SERVIDOR;
import static principal.Consts.PORTA_SERVIDOR;

public class ListarProduto extends JPanel implements ActionListener {

    private final JTable tabela = new JTable();
    private JScrollPane barraRolagem;
    private DefaultTableModel model;

    public ListarProduto(JButton botaoListar) {
        botaoListar.addActionListener(this);
        init();
    }

    void init() {
        model = new DefaultTableModel(new String[][]{},
                new String[]{"Nome", "Preço", "Quantidade"});
        removeAll();
        revalidate();
        try {
            InterfaceProduto produtoRemoto = (InterfaceProduto) Naming.lookup("rmi://" + IP_SERVIDOR + ":" + PORTA_SERVIDOR + "/Produto");

            ArrayList<InterfaceProduto> produtos = produtoRemoto.listar();

            for (InterfaceProduto p : produtos) {
                model.addRow(new Object[]{p.getDescricao(), p.getPreco(),
                    p.getQuantidade()});
            }

        } catch (RemoteException re) {
            System.err.println("Erro remoto: " + re.toString());
        } catch (Exception e) {
            System.err.println("Erro local: " + e.toString());
        }

        tabela.setModel(model);

        tabela.getColumnModel().getColumn(0).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(50);

        barraRolagem = new JScrollPane(tabela);
        barraRolagem.setPreferredSize(new Dimension(300, 150));
        add(barraRolagem);
        repaint();
        validate();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        init();
    }
}
