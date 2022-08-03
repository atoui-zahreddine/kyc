import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './applicant-level.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ApplicantLevelDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const applicantLevelEntity = useAppSelector(state => state.applicantLevel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="applicantLevelDetailsHeading">ApplicantLevel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{applicantLevelEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{applicantLevelEntity.code}</dd>
          <dt>
            <span id="levelName">Level Name</span>
          </dt>
          <dd>{applicantLevelEntity.levelName}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{applicantLevelEntity.description}</dd>
          <dt>
            <span id="url">Url</span>
          </dt>
          <dd>{applicantLevelEntity.url}</dd>
          <dt>
            <span id="createdAt">Created At</span>
          </dt>
          <dd>
            {applicantLevelEntity.createdAt ? (
              <TextFormat value={applicantLevelEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdBy">Created By</span>
          </dt>
          <dd>{applicantLevelEntity.createdBy}</dd>
          <dt>
            <span id="modifiedAt">Modified At</span>
          </dt>
          <dd>
            {applicantLevelEntity.modifiedAt ? (
              <TextFormat value={applicantLevelEntity.modifiedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Step</dt>
          <dd>
            {applicantLevelEntity.steps
              ? applicantLevelEntity.steps.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {applicantLevelEntity.steps && i === applicantLevelEntity.steps.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/applicant-level" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/applicant-level/${applicantLevelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ApplicantLevelDetail;
