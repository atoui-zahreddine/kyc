import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './country.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CountryDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const countryEntity = useAppSelector(state => state.country.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="countryDetailsHeading">Country</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{countryEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{countryEntity.name}</dd>
          <dt>
            <span id="countryCode2">Country Code 2</span>
          </dt>
          <dd>{countryEntity.countryCode2}</dd>
          <dt>
            <span id="countryCode3">Country Code 3</span>
          </dt>
          <dd>{countryEntity.countryCode3}</dd>
          <dt>
            <span id="phoneCode">Phone Code</span>
          </dt>
          <dd>{countryEntity.phoneCode}</dd>
          <dt>
            <span id="region">Region</span>
          </dt>
          <dd>{countryEntity.region}</dd>
        </dl>
        <Button tag={Link} to="/country" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/country/${countryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CountryDetail;
