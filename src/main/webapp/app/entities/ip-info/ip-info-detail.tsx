import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './ip-info.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const IpInfoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const ipInfoEntity = useAppSelector(state => state.ipInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ipInfoDetailsHeading">IpInfo</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ipInfoEntity.id}</dd>
          <dt>
            <span id="asn">Asn</span>
          </dt>
          <dd>{ipInfoEntity.asn}</dd>
          <dt>
            <span id="asnOrg">Asn Org</span>
          </dt>
          <dd>{ipInfoEntity.asnOrg}</dd>
          <dt>
            <span id="countryCode2">Country Code 2</span>
          </dt>
          <dd>{ipInfoEntity.countryCode2}</dd>
          <dt>
            <span id="countryCode3">Country Code 3</span>
          </dt>
          <dd>{ipInfoEntity.countryCode3}</dd>
          <dt>
            <span id="ip">Ip</span>
          </dt>
          <dd>{ipInfoEntity.ip}</dd>
          <dt>
            <span id="lat">Lat</span>
          </dt>
          <dd>{ipInfoEntity.lat}</dd>
          <dt>
            <span id="lon">Lon</span>
          </dt>
          <dd>{ipInfoEntity.lon}</dd>
          <dt>Applicant</dt>
          <dd>{ipInfoEntity.applicant ? ipInfoEntity.applicant.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/ip-info" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ip-info/${ipInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default IpInfoDetail;
