package com.hrh.kmanual.sys.dao.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author huangrenhao
 * @date 2018/9/6
 */
@Table(name = "sys_sequence")
@Entity
@Getter
@Setter
public class Sequence {

    @Id
    private Integer no;

    public Sequence() {
    }

    public Sequence(Integer no) {
        this.no = no;
    }
}
