/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        
        conn = new conectaDAO().connectDB();
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + erro.getMessage());
        }
    }
    
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        String sql = "SELECT * FROM produtos";
        conn = new conectaDAO().connectDB();
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();

        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Listar Produtos DAO: " + erro);
        }
        return listagem;
    }
    
    public void venderProduto(int idProduto) {
            String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

            try (Connection conn = new conectaDAO().connectDB();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, idProduto);
                stmt.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        ArrayList<ProdutosDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";

        try (Connection conn = new conectaDAO().connectDB();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProdutosDTO dto = new ProdutosDTO();
                dto.setId(rs.getInt("id"));
                dto.setNome(rs.getString("nome"));
                dto.setValor(rs.getInt("valor"));
                dto.setStatus(rs.getString("status"));
                lista.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    
}

