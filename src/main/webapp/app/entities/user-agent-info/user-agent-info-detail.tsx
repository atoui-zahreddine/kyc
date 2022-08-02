import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './user-agent-info.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const UserAgentInfoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const userAgentInfoEntity = useAppSelector(state => state.userAgentInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userAgentInfoDetailsHeading">UserAgentInfo</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{userAgentInfoEntity.id}</dd>
          <dt>
            <span id="uaBrowser">Ua Browser</span>
          </dt>
          <dd>{userAgentInfoEntity.uaBrowser}</dd>
          <dt>
            <span id="uaBrowserVersion">Ua Browser Version</span>
          </dt>
          <dd>{userAgentInfoEntity.uaBrowserVersion}</dd>
          <dt>
            <span id="uaDeviceType">Ua Device Type</span>
          </dt>
          <dd>{userAgentInfoEntity.uaDeviceType}</dd>
          <dt>
            <span id="uaPlatform">Ua Platform</span>
          </dt>
          <dd>{userAgentInfoEntity.uaPlatform}</dd>
          <dt>Applicant</dt>
          <dd>{userAgentInfoEntity.applicant ? userAgentInfoEntity.applicant.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-agent-info" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-agent-info/${userAgentInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserAgentInfoDetail;
