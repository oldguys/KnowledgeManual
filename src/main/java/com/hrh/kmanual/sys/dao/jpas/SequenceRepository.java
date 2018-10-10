package com.hrh.kmanual.sys.dao.jpas;

import com.hrh.kmanual.sys.dao.entities.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author huangrenhao
 * @date 2018/9/6
 */
public interface SequenceRepository extends JpaRepository<Sequence, Integer> {
}
