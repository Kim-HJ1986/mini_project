package com.coffee.miniproject.util;

<<<<<<< HEAD
=======
import jdk.jfr.Timestamp;
>>>>>>> 1c28ef9 ([etc]First Commit)
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter // private일 때는 Getter 꼭 써줘야한다!
@MappedSuperclass // Entity가 자동으로 컬럼으로 인식합니다.
@EntityListeners(AuditingEntityListener.class) // 생성/변경 시간을 자동으로 업데이트합니다.
public class Timestamped {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
<<<<<<< HEAD
}
=======
}
>>>>>>> 1c28ef9 ([etc]First Commit)
