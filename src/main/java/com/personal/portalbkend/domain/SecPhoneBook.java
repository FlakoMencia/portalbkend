package com.personal.portalbkend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "sec_phone_book", schema = "public", catalog = "postgres")
public class SecPhoneBook implements Serializable {


    @Serial
    private static final long serialVersionUID = -4216388585695213851L;

    @Id
    @Column(name = "id_phone", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_sec_phone")
    @SequenceGenerator(name = "seq_sec_phone", sequenceName = "seq_sec_phone", allocationSize = 1)
    private Long idPhone;


    @Basic
    @Column(name = "nu_number", nullable = false)
    private Integer nuNumber;
    @Basic
    @Column(name = "cd_city_code")
    private Integer cdCityCode;
    @Basic
    @Column(name = "cd_contry_code")
    private Integer cdContryCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private SecUser secUserByIdUser;

}
