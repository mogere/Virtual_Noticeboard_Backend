package dao;
import models.Notice;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.util.List;

public class Sql2oNoticeDao implements NoticeDao {

    private final Sql2o sql2o;
    public Sql2oNoticeDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Notice notice){
        String sql = "INSERT INTO notice (title,noticeContent) VALUES (:title,:noticeContent)";
        try(Connection con = sql2o.open()){
            int noticeId = (int) con.createQuery(sql,true).bind(notice).executeUpdate().getKey();
            notice.setNoticeId(noticeId);

        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
    @Override
    public List<Notice> getAll(){
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM notices").executeAndFetch(Notice.class);

        }
    }

    @Override
    public Notice findNoticeById(int noticeId){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM notices WHERE noticeId = :noticeId")
                    .addParameter("noticeId",noticeId).executeAndFetchFirst(Notice.class);
        }

    }

    @Override
    public void deleteNoticeById(int noticeId) {
        String sql = "DELETE from notices WHERE noticeId=:noticeId"; //raw sql
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("noticeId", noticeId)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll(){
        String sql = "DELETE FROM notice";
        try (Connection connection = sql2o.open()){
            connection.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){ System.out.println(ex);}
    }

}
