package ir.mctab.java32.hw10.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Article")

public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = 22)
    private String title;

    @Column(name = "brief", nullable = false, length = 80)
    private String brief;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @Column(name = "createDate", nullable = false, length = 22)
    private String createDate;

    @Column(name = "lastUpdate", nullable = true, length = 22)
    private String lastUpdateDate;

    @Column(name = "publishDate", nullable = true, length = 22)
    private String publishDate;

    @Column(name = "isPublish", nullable = false)
    private boolean isPublish;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    @ManyToMany
    @JoinTable(name = "Article_Tags"
            , joinColumns = @JoinColumn(name = "Article_Id")
            , inverseJoinColumns = @JoinColumn(name = "Tag_Id"))
    private Set<Tag> tagSet;

    public Article(String title, String brief, String content, String createDate, boolean isPublish, User user, Category category) {
        this.title = title;
        this.brief = brief;
        this.content = content;
        this.createDate = createDate;
        this.isPublish = isPublish;
        this.user = user;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", brief='" + brief + '\'' +
                ", content='" + content + '\'' +
                ", createDate='" + createDate + '\'' +
                ", lastUpdateDate='" + lastUpdateDate + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", isPublish=" + isPublish +
                ", user=" + user +
                ", category=" + category +
                ", tagSet=" + tagSet +
                '}';
    }
}



