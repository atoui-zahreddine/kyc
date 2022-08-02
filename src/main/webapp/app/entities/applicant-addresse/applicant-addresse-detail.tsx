import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './applicant-addresse.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ApplicantAddresseDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const applicantAddresseEntity = useAppSelector(state => state.applicantAddresse.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="applicantAddresseDetailsHeading">ApplicantAddresse</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{applicantAddresseEntity.id}</dd>
          <dt>
            <span id="postCode">Post Code</span>
          </dt>
          <dd>{applicantAddresseEntity.postCode}</dd>
          <dt>
            <span id="state">State</span>
          </dt>
          <dd>{applicantAddresseEntity.state}</dd>
          <dt>
            <span id="street">Street</span>
          </dt>
          <dd>{applicantAddresseEntity.street}</dd>
          <dt>
            <span id="subStreet">Sub Street</span>
          </dt>
          <dd>{applicantAddresseEntity.subStreet}</dd>
          <dt>
            <span id="town">Town</span>
          </dt>
          <dd>{applicantAddresseEntity.town}</dd>
          <dt>
            <span id="enabled">Enabled</span>
          </dt>
          <dd>{applicantAddresseEntity.enabled ? 'true' : 'false'}</dd>
          <dt>Applicant Info</dt>
          <dd>{applicantAddresseEntity.applicantInfo ? applicantAddresseEntity.applicantInfo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/applicant-addresse" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/applicant-addresse/${applicantAddresseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ApplicantAddresseDetail;
