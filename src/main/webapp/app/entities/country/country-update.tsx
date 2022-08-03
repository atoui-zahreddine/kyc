import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './country.reducer';
import { ICountry } from 'app/shared/model/country.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { CountryRegion } from 'app/shared/model/enumerations/country-region.model';

export const CountryUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const countryEntity = useAppSelector(state => state.country.entity);
  const loading = useAppSelector(state => state.country.loading);
  const updating = useAppSelector(state => state.country.updating);
  const updateSuccess = useAppSelector(state => state.country.updateSuccess);
  const countryRegionValues = Object.keys(CountryRegion);
  const handleClose = () => {
    props.history.push('/country' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...countryEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          region: 'Africa',
          ...countryEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="kycApp.country.home.createOrEditLabel" data-cy="CountryCreateUpdateHeading">
            Create or edit a Country
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="country-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Name" id="country-name" name="name" data-cy="name" type="text" />
              <ValidatedField label="Country Code 2" id="country-countryCode2" name="countryCode2" data-cy="countryCode2" type="text" />
              <ValidatedField label="Country Code 3" id="country-countryCode3" name="countryCode3" data-cy="countryCode3" type="text" />
              <ValidatedField label="Phone Code" id="country-phoneCode" name="phoneCode" data-cy="phoneCode" type="text" />
              <ValidatedField label="Region" id="country-region" name="region" data-cy="region" type="select">
                {countryRegionValues.map(countryRegion => (
                  <option value={countryRegion} key={countryRegion}>
                    {countryRegion}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/country" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CountryUpdate;
