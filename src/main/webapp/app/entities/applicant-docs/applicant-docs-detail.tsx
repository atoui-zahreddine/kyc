import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './applicant-docs.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ApplicantDocsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const applicantDocsEntity = useAppSelector(state => state.applicantDocs.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="applicantDocsDetailsHeading">ApplicantDocs</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{applicantDocsEntity.id}</dd>
          <dt>
            <span id="docType">Doc Type</span>
          </dt>
          <dd>{applicantDocsEntity.docType}</dd>
          <dt>
            <span id="firstName">First Name</span>
          </dt>
          <dd>{applicantDocsEntity.firstName}</dd>
          <dt>
            <span id="lastName">Last Name</span>
          </dt>
          <dd>{applicantDocsEntity.lastName}</dd>
          <dt>
            <span id="number">Number</span>
          </dt>
          <dd>{applicantDocsEntity.number}</dd>
          <dt>
            <span id="dateOfBirth">Date Of Birth</span>
          </dt>
          <dd>
            {applicantDocsEntity.dateOfBirth ? (
              <TextFormat value={applicantDocsEntity.dateOfBirth} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="validUntil">Valid Until</span>
          </dt>
          <dd>
            {applicantDocsEntity.validUntil ? (
              <TextFormat value={applicantDocsEntity.validUntil} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="imageUrl">Image Url</span>
          </dt>
          <dd>{applicantDocsEntity.imageUrl}</dd>
          <dt>
            <span id="subTypes">Sub Types</span>
          </dt>
          <dd>{applicantDocsEntity.subTypes}</dd>
          <dt>
            <span id="imageTrust">Image Trust</span>
          </dt>
          <dd>{applicantDocsEntity.imageTrust}</dd>
          <dt>Applicant Info</dt>
          <dd>{applicantDocsEntity.applicantInfo ? applicantDocsEntity.applicantInfo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/applicant-docs" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/applicant-docs/${applicantDocsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ApplicantDocsDetail;
