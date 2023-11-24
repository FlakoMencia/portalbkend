package com.personal.portalbkend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "sec_role", schema = "public", catalog = "postgres")
public class SecRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 5580006560121830421L;

    @Id
    @Column(name = "id_role", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_sec_role")
    @SequenceGenerator(name = "seq_sec_role", sequenceName = "seq_sec_role", allocationSize = 1)
    private Long idRole;

    @Basic
    @Column(name = "cd_role", nullable = false, length = 50)
    private String cdRole;
    @Basic
    @Column(name = "st_description", nullable = false, length = 256)
    private String stDescription;
    @Basic
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    @Basic
    @Column(name = "st_cre_user", nullable = false, length = 150)
    private String stCreUser;
    @Basic
    @Column(name = "dt_create", nullable = false)
    private Date dtCreate;
    @Basic
    @Column(name = "st_mod_user", nullable = true, length = 150)
    private String stModUser;
    @Basic
    @Column(name = "dt_modify", nullable = true)
    private Date dtModify;

}
