import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICountry } from 'app/shared/model/country.model';
import { getEntities as getCountries } from 'app/entities/country/country.reducer';
import { IApplicantInfo } from 'app/shared/model/applicant-info.model';
import { getEntities as getApplicantInfos } from 'app/entities/applicant-info/applicant-info.reducer';
import { getEntity, updateEntity, createEntity, reset } from './applicant-docs.reducer';
import { IApplicantDocs } from 'app/shared/model/applicant-docs.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { TypeDoc } from 'app/shared/model/enumerations/type-doc.model';
import { SubType } from 'app/shared/model/enumerations/sub-type.model';

export const ApplicantDocsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const countries = useAppSelector(state => state.country.entities);
  const applicantInfos = useAppSelector(state => state.applicantInfo.entities);
  const applicantDocsEntity = useAppSelector(state => state.applicantDocs.entity);
  const loading = useAppSelector(state => state.applicantDocs.loading);
  const updating = useAppSelector(state => state.applicantDocs.updating);
  const updateSuccess = useAppSelector(state => state.applicantDocs.updateSuccess);
  const typeDocValues = Object.keys(TypeDoc);
  const subTypeValues = Object.keys(SubType);
  const handleClose = () => {
    props.history.push('/applicant-docs' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCountries({}));
    dispatch(getApplicantInfos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...applicantDocsEntity,
      ...values,
      docsCountry: countries.find(it => it.id.toString() === values.docsCountry.toString()),
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
          docType: 'ID_CARD',
          subTypes: 'FRONT_SIDE',
          ...applicantDocsEntity,
          docsCountry: applicantDocsEntity?.docsCountry?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="kycApp.applicantDocs.home.createOrEditLabel" data-cy="ApplicantDocsCreateUpdateHeading">
            Create or edit a ApplicantDocs
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="applicant-docs-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Doc Type" id="applicant-docs-docType" name="docType" data-cy="docType" type="select">
                {typeDocValues.map(typeDoc => (
                  <option value={typeDoc} key={typeDoc}>
                    {typeDoc}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="First Name" id="applicant-docs-firstName" name="firstName" data-cy="firstName" type="text" />
              <ValidatedField label="Last Name" id="applicant-docs-lastName" name="lastName" data-cy="lastName" type="text" />
              <ValidatedField label="Number" id="applicant-docs-number" name="number" data-cy="number" type="text" />
              <ValidatedField label="Date Of Birth" id="applicant-docs-dateOfBirth" name="dateOfBirth" data-cy="dateOfBirth" type="date" />
              <ValidatedField label="Valid Until" id="applicant-docs-validUntil" name="validUntil" data-cy="validUntil" type="date" />
              <ValidatedField label="Image Url" id="applicant-docs-imageUrl" name="imageUrl" data-cy="imageUrl" type="text" />
              <ValidatedField label="Sub Types" id="applicant-docs-subTypes" name="subTypes" data-cy="subTypes" type="select">
                {subTypeValues.map(subType => (
                  <option value={subType} key={subType}>
                    {subType}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Image Trust" id="applicant-docs-imageTrust" name="imageTrust" data-cy="imageTrust" type="text" />
              <ValidatedField id="applicant-docs-docsCountry" name="docsCountry" data-cy="docsCountry" label="Docs Country" type="select">
                <option value="" key="0" />
                {countries
                  ? countries.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/applicant-docs" replace color="info">
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

export default ApplicantDocsUpdate;
