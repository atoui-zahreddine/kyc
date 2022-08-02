import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './applicant-phone.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ApplicantPhoneDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const applicantPhoneEntity = useAppSelector(state => state.applicantPhone.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="applicantPhoneDetailsHeading">ApplicantPhone</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{applicantPhoneEntity.id}</dd>
          <dt>
            <span id="country">Country</span>
          </dt>
          <dd>{applicantPhoneEntity.country}</dd>
          <dt>
            <span id="number">Number</span>
          </dt>
          <dd>{applicantPhoneEntity.number}</dd>
          <dt>
            <span id="enabled">Enabled</span>
          </dt>
          <dd>{applicantPhoneEntity.enabled ? 'true' : 'false'}</dd>
          <dt>Applicant Info</dt>
          <dd>{applicantPhoneEntity.applicantInfo ? applicantPhoneEntity.applicantInfo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/applicant-phone" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/applicant-phone/${applicantPhoneEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ApplicantPhoneDetail;