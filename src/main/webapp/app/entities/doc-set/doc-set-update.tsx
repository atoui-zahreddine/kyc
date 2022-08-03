import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IStep } from 'app/shared/model/step.model';
import { getEntities as getSteps } from 'app/entities/step/step.reducer';
import { getEntity, updateEntity, createEntity, reset } from './doc-set.reducer';
import { IDocSet } from 'app/shared/model/doc-set.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { IdDocSetType } from 'app/shared/model/enumerations/id-doc-set-type.model';
import { SubType } from 'app/shared/model/enumerations/sub-type.model';
import { TypeDoc } from 'app/shared/model/enumerations/type-doc.model';

export const DocSetUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const steps = useAppSelector(state => state.step.entities);
  const docSetEntity = useAppSelector(state => state.docSet.entity);
  const loading = useAppSelector(state => state.docSet.loading);
  const updating = useAppSelector(state => state.docSet.updating);
  const updateSuccess = useAppSelector(state => state.docSet.updateSuccess);
  const idDocSetTypeValues = Object.keys(IdDocSetType);
  const subTypeValues = Object.keys(SubType);
  const typeDocValues = Object.keys(TypeDoc);
  const handleClose = () => {
    props.history.push('/doc-set' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getSteps({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...docSetEntity,
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
          idDocSetType: 'IDENTITY',
          subTypes: 'FRONT_SIDE',
          types: 'ID_CARD',
          ...docSetEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="kycApp.docSet.home.createOrEditLabel" data-cy="DocSetCreateUpdateHeading">
            Create or edit a DocSet
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="doc-set-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Id Doc Set Type" id="doc-set-idDocSetType" name="idDocSetType" data-cy="idDocSetType" type="select">
                {idDocSetTypeValues.map(idDocSetType => (
                  <option value={idDocSetType} key={idDocSetType}>
                    {idDocSetType}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Sub Types" id="doc-set-subTypes" name="subTypes" data-cy="subTypes" type="select">
                {subTypeValues.map(subType => (
                  <option value={subType} key={subType}>
                    {subType}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Types" id="doc-set-types" name="types" data-cy="types" type="select">
                {typeDocValues.map(typeDoc => (
                  <option value={typeDoc} key={typeDoc}>
                    {typeDoc}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/doc-set" replace color="info">
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

export default DocSetUpdate;
