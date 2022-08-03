import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './step.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StepDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const stepEntity = useAppSelector(state => state.step.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="stepDetailsHeading">Step</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{stepEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{stepEntity.code}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{stepEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{stepEntity.description}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>{stepEntity.createdAt ? <TextFormat value={stepEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdBy">Created By</span>
          </dt>
          <dd>{stepEntity.createdBy}</dd>
          <dt>
            <span id="modifiedAt">Modified At</span>
          </dt>
          <dd>{stepEntity.modifiedAt ? <TextFormat value={stepEntity.modifiedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Doc Set</dt>
          <dd>
            {stepEntity.docSets
              ? stepEntity.docSets.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {stepEntity.docSets && i === stepEntity.docSets.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/step" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/step/${stepEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default StepDetail;
