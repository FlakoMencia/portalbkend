package com.personal.portalbkend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "sec_user", schema = "public", catalog = "postgres")
public class SecUser implements Serializable {


    @Serial
    private static final long serialVersionUID = 782772160844731522L;

    @Id
    @Column(name = "id_user", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_sec_user")
    @SequenceGenerator(name = "seq_sec_user", sequenceName = "seq_sec_user", allocationSize = 1)
    private Long idUser;

    @Basic
    @Column(name = "st_name", nullable = true, length = -1)
    private String stName;
    @Basic
    @Column(name = "st_email", nullable = true, length = -1)
    private String stEmail;
    @Basic
    @Column(name = "st_password", nullable = false, length = 100)
    private String stPassword;
    @Basic
    @Column(name = "st_cre_user", nullable = true, length = 150)
    private String stCreUser;
    @Basic
    @Column(name = "dt_create", nullable = false)
    private Date dtCreate;
    @Basic
    @Column(name = "is_active", nullable = true)
    private Boolean isActive;
    @Basic
    @Column(name = "st_mod_user", nullable = true, length = 150)
    private String stModUser;
    @Basic
    @Column(name = "dt_modify", nullable = true)
    private Date dtModify;
    @Basic
    @Column(name = "jw_token", nullable = true, length = -1)
    private String jwToken;
    @Basic
    @Column(name = "dt_last_connect", nullable = true)
    private Date dtLastConnection;

    @OneToMany(mappedBy = "secUserByFkUser", fetch = FetchType.EAGER)
    @Getter(onMethod = @__(@JsonIgnore))
    @ToString.Exclude
    private Set<SecUserRole> secUserRoles;

    @OneToMany(mappedBy = "secUserByIdUser", fetch = FetchType.EAGER)
    @Getter(onMethod = @__(@JsonIgnore))
    @ToString.Exclude
    private Set<SecPhoneBook> secPhoneBook;

}
