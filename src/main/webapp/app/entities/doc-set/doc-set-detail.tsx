import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './doc-set.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DocSetDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const docSetEntity = useAppSelector(state => state.docSet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="docSetDetailsHeading">DocSet</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{docSetEntity.id}</dd>
          <dt>
            <span id="idDocSetType">Id Doc Set Type</span>
          </dt>
          <dd>{docSetEntity.idDocSetType}</dd>
          <dt>
            <span id="subTypes">Sub Types</span>
          </dt>
          <dd>{docSetEntity.subTypes}</dd>
          <dt>
            <span id="types">Types</span>
          </dt>
          <dd>{docSetEntity.types}</dd>
          <dt>Step</dt>
          <dd>{docSetEntity.step ? docSetEntity.step.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/doc-set" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/doc-set/${docSetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DocSetDetail;
