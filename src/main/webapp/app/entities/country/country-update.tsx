import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IApplicantAddresse } from 'app/shared/model/applicant-addresse.model';
import { getEntities as getApplicantAddresses } from 'app/entities/applicant-addresse/applicant-addresse.reducer';
import { IApplicantDocs } from 'app/shared/model/applicant-docs.model';
import { getEntities as getApplicantDocs } from 'app/entities/applicant-docs/applicant-docs.reducer';
import { IApplicantInfo } from 'app/shared/model/applicant-info.model';
import { getEntities as getApplicantInfos } from 'app/entities/applicant-info/applicant-info.reducer';
import { IApplicantPhone } from 'app/shared/model/applicant-phone.model';
import { getEntities as getApplicantPhones } from 'app/entities/applicant-phone/applicant-phone.reducer';
import { getEntity, updateEntity, createEntity, reset } from './country.reducer';
import { ICountry } from 'app/shared/model/country.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { CountryRegion } from 'app/shared/model/enumerations/country-region.model';

export const CountryUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const applicantAddresses = useAppSelector(state => state.applicantAddresse.entities);
  const applicantDocs = useAppSelector(state => state.applicantDocs.entities);
  const applicantInfos = useAppSelector(state => state.applicantInfo.entities);
  const applicantPhones = useAppSelector(state => state.applicantPhone.entities);
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

    dispatch(getApplicantAddresses({}));
    dispatch(getApplicantDocs({}));
    dispatch(getApplicantInfos({}));
    dispatch(getApplicantPhones({}));
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
      addresses: applicantAddresses.find(it => it.id.toString() === values.addresses.toString()),
      docs: applicantDocs.find(it => it.id.toString() === values.docs.toString()),
      applicants: applicantInfos.find(it => it.id.toString() === values.applicants.toString()),
      phones: applicantPhones.find(it => it.id.toString() === values.phones.toString()),
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
          addresses: countryEntity?.addresses?.id,
          docs: countryEntity?.docs?.id,
          applicants: countryEntity?.applicants?.id,
          phones: countryEntity?.phones?.id,
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
              <ValidatedField id="country-addresses" name="addresses" data-cy="addresses" label="Addresses" type="select">
                <option value="" key="0" />
                {applicantAddresses
                  ? applicantAddresses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="country-docs" name="docs" data-cy="docs" label="Docs" type="select">
                <option value="" key="0" />
                {applicantDocs
                  ? applicantDocs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="country-applicants" name="applicants" data-cy="applicants" label="Applicants" type="select">
                <option value="" key="0" />
                {applicantInfos
                  ? applicantInfos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="country-phones" name="phones" data-cy="phones" label="Phones" type="select">
                <option value="" key="0" />
                {applicantPhones
                  ? applicantPhones.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
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
