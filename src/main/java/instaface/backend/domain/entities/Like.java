package instaface.backend.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "likes")
public class Like extends BaseEntity {
    private Long count = 0L;    //Số lượng like
    private User user;          //Người like
    private Post post;          //Bài đăng

    public Like() {
    }

    @Column(name = "count")
    public Long getCount() {
        return this.count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name= "user_id", referencedColumnName = "id")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name= "post_id", referencedColumnName = "id")
    public Post getPost() {
        return this.post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
