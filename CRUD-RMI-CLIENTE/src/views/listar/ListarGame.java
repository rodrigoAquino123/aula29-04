package views.listar;

import interfaces.InterfaceGame;
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

public class ListarGame extends JPanel implements ActionListener {

    private final JTable tabela = new JTable();
    private JScrollPane barraRolagem;
    private DefaultTableModel model;

    public ListarGame(JButton botaoListar) {
        botaoListar.addActionListener(this);
        init();
    }

    void init() {
        model = new DefaultTableModel(new String[][]{},
                new String[]{"Nome", "Ano", "Nota"});
        removeAll();
        revalidate();
        try {
            InterfaceGame gameRemoto = (InterfaceGame) Naming.lookup("rmi://" + IP_SERVIDOR + ":" + PORTA_SERVIDOR + "/Game");

            ArrayList<InterfaceGame> games = gameRemoto.listar();

            for (InterfaceGame g : games) {
                model.addRow(new Object[]{g.getNome(), g.getAno(),
                    g.getNota()});
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
