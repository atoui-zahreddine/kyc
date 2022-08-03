import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './applicant-info.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ApplicantInfoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const applicantInfoEntity = useAppSelector(state => state.applicantInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="applicantInfoDetailsHeading">ApplicantInfo</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{applicantInfoEntity.id}</dd>
          <dt>
            <span id="firstName">First Name</span>
          </dt>
          <dd>{applicantInfoEntity.firstName}</dd>
          <dt>
            <span id="lastName">Last Name</span>
          </dt>
          <dd>{applicantInfoEntity.lastName}</dd>
          <dt>
            <span id="addresses">Addresses</span>
          </dt>
          <dd>{applicantInfoEntity.addresses}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{applicantInfoEntity.email}</dd>
          <dt>
            <span id="middleName">Middle Name</span>
          </dt>
          <dd>{applicantInfoEntity.middleName}</dd>
          <dt>
            <span id="stateOfBirth">State Of Birth</span>
          </dt>
          <dd>{applicantInfoEntity.stateOfBirth}</dd>
          <dt>
            <span id="dateOfBirth">Date Of Birth</span>
          </dt>
          <dd>
            {applicantInfoEntity.dateOfBirth ? (
              <TextFormat value={applicantInfoEntity.dateOfBirth} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="placeOfBirth">Place Of Birth</span>
          </dt>
          <dd>{applicantInfoEntity.placeOfBirth}</dd>
          <dt>
            <span id="nationality">Nationality</span>
          </dt>
          <dd>{applicantInfoEntity.nationality}</dd>
          <dt>
            <span id="gender">Gender</span>
          </dt>
          <dd>{applicantInfoEntity.gender}</dd>
          <dt>Applicant</dt>
          <dd>{applicantInfoEntity.applicant ? applicantInfoEntity.applicant.id : ''}</dd>
          <dt>Country Of Birth</dt>
          <dd>{applicantInfoEntity.countryOfBirth ? applicantInfoEntity.countryOfBirth.id : ''}</dd>
          <dt>Applicant Addresse</dt>
          <dd>
            {applicantInfoEntity.applicantAddresses
              ? applicantInfoEntity.applicantAddresses.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {applicantInfoEntity.applicantAddresses && i === applicantInfoEntity.applicantAddresses.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Applicant Phone</dt>
          <dd>
            {applicantInfoEntity.applicantPhones
              ? applicantInfoEntity.applicantPhones.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {applicantInfoEntity.applicantPhones && i === applicantInfoEntity.applicantPhones.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Applicant Docs</dt>
          <dd>
            {applicantInfoEntity.applicantDocs
              ? applicantInfoEntity.applicantDocs.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {applicantInfoEntity.applicantDocs && i === applicantInfoEntity.applicantDocs.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/applicant-info" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/applicant-info/${applicantInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ApplicantInfoDetail;
