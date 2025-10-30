package br.com.fiap.loja.dao;

import br.com.fiap.loja.model.Avaliacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class AvaliacaoDao {

    @Inject
    private DataSource dataSource;

    public void cadastrar(Avaliacao avaliacao) throws SQLException {
        try (Connection conexao = dataSource.getConnection()){
            PreparedStatement stmt = conexao.prepareStatement("insert int t_tdspw_avaliacao (cd_avaliacao," +
                    "ds_avaliacao, vl_nota, dt_cadastro, cd_doce) values (sq_tdspw_avaliacao.nextval, ?, ?, sysdate,?)", new String[]{"cd_avaliacao"});
            stmt.setString(1, avaliacao.getDescricao());
            stmt.setDouble(2, avaliacao.getNota());
            stmt.setInt(3, avaliacao.getCodigoDoce());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                avaliacao.setCodigo(rs.getInt(1));

        }
    }
}
