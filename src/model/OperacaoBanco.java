package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * Classe Principal para as Operações Banco
 * @author Divino Silva
 * @since Classe criada em 22/06/2016
 */
public class OperacaoBanco {
    Connection con=null;
    public OperacaoBanco(){
        this.carregarDriver();
    }
    
    public void carregarDriver(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null,"O driver MySQL não pode ser carregado. ERRO: "+ ex.getMessage());
        }
    }
    
    public Connection obterConexao(){
        try{
            if((con==null)||(con.isClosed()))
                con= DriverManager.getConnection("jdbc:mysql://localhost/senai", "root","");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"SQLException: "+ex.getMessage());
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Exception: "+ex.getMessage());
        }
        return con;
    }
    
    public void fecharConexao(){
        try{
            if((con==null)||(con.isClosed()))
                con.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"SQLException: "+ex.getMessage());
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Exception: "+ex.getMessage());
        }
    }    

    public void incluir(Contato xy){      
        Connection conexao = this.obterConexao();
        PreparedStatement pre = null;
        try{
            String sql = "Insert into contatos (nome, assunto, mensagem) values (?,?,?)";
            pre=conexao.prepareStatement(sql);
            pre.setString(1, xy.getNome());
            pre.setString(2, xy.getAssunto());
            pre.setString(3, xy.getMensagem());
            pre.executeUpdate();
            JOptionPane.showMessageDialog(null, "Inclusão realizada com sucesso.");
       } catch(SQLException a) {
            JOptionPane.showMessageDialog(null, "Erro: " + a.getMessage());
        }
    }
    
    
    public ResultSet listar() {
        Statement st;
        ResultSet rs = null;
        try {
            Connection conexao = this.obterConexao();
            st = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery("Select * from contatos order by nome");
            rs.first();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return rs;
    }

    public ResultSet buscarPorNome(String nome) {
        PreparedStatement st;
        ResultSet rs = null;
        try {
            Connection conexao = this.obterConexao();
            st = conexao.prepareStatement("Select * from contatos where nome like ?");
            st.setString(1, nome + '%');
            rs = st.executeQuery();
            rs.first();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
        return rs;
    }    
    
}//fim da clase


    
 
  