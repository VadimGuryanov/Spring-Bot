package ru.kpfu.itis.springbots.models;

import javax.persistence.*;

@Entity
@Table(name = "backword")
public class Backward {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private long id;

    @Column(nullable = false)
    private String name;

}
