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
@Table(name = "sec_user_role", schema = "public", catalog = "postgres")
public class SecUserRole implements Serializable {


    @Serial
    private static final long serialVersionUID = -8819853340678774811L;

    @Id
    @Column(name = "id_user_role", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_sec_user_role")
    @SequenceGenerator(name = "seq_sec_user_role", sequenceName = "seq_sec_user_role", allocationSize = 1)
    private Long idUserRole;

    @Basic
    @Column(name = "st_cre_user", nullable = true, length = 150)
    private String stCreUser;
    @Basic
    @Column(name = "dt_create", nullable = false)
    private Date dtCreate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user", referencedColumnName = "id_user", nullable = false)
    private SecUser secUserByFkUser;
    @ManyToOne
    @JoinColumn(name = "fk_role", referencedColumnName = "id_role", nullable = false)
    private SecRole secRoleByFkRole;


}
