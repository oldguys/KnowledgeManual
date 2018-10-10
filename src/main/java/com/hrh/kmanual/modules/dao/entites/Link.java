package com.hrh.kmanual.modules.dao.entites;

import com.hrh.kmanual.commons.dao.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author huangrenhao
 * @date 2018/9/7
 */
@Entity
@Getter
@Setter
public class Link extends BaseEntity {

    private String href;

    private String tags;

    private String description;

}
