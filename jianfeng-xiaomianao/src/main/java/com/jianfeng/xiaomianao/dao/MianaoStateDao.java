package com.jianfeng.xiaomianao.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jianfeng.xiaomianao.domain.MianaoState;

@Repository
public class MianaoStateDao extends AbstractDao<MianaoState, Integer> {

    private Logger logger = LoggerFactory.getLogger(MianaoStateDao.class);

    private static final String FIND_All = "select s from MianaoState s";

    private static final String FIND_BY_NAME = "select s from MianaoState s where s.name in (:states)";

    public List<MianaoState> findStates() {
        return entityManager.createQuery(FIND_All, MianaoState.class).getResultList();
    }

    public List<MianaoState> findStatesByName(List<String> states) {
        return entityManager.createQuery(FIND_BY_NAME, MianaoState.class).setParameter("states", states).getResultList();
    }
}
