package instaface.backend.domain.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
//Log lưu trữ lịch sử, nhật ký người dùng
@Entity
@Table(name = "logs")
public class Logger extends BaseEntity{
    private String username;    //Tên người dùng
    private String method;      //Các method: POST, GET,...
    private String tableName;
    private String action;
    private LocalDateTime time;

    public Logger() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
