import React, { FunctionComponent } from 'react';
import { IStep } from 'app/shared/model/step.model';
import { CommandBarButton, DefaultButton, Dropdown, mergeStyles, Stack, TextField } from '@fluentui/react';
import { Controller, useWatch } from 'react-hook-form';
import { IdDocSetType } from 'app/shared/model/enumerations/id-doc-set-type.model';
import { TypeDoc } from 'app/shared/model/enumerations/type-doc.model';

const options = [
  { key: 'apple', text: 'Apple' },
  { key: 'banana', text: 'Banana' },
  { key: 'orange', text: 'Orange' },
  { key: 'grape', text: 'Grape' },
  { key: 'broccoli', text: 'Broccoli' },
  { key: 'carrot', text: 'Carrot' },
  { key: 'lettuce', text: 'Lettuce' },
];

const fileStyle = mergeStyles({
  width: 0,
});
const labelStyle = mergeStyles({
  alignSelf: 'stretch',
});
const inputStyle = { root: { flex: 1 } };

interface ProofOfResidenceDocumentsProps {
  onChangeHandler: (option, target: string) => void;
  control: any;
}

interface IdentityDocumentsProps {
  step: IStep;
  control: any;
  onChangeHandler: any;
}

const UploadFile = ({ id, control, target, onChangeHandler }) => {
  const file = useWatch({ control, name: `${target}.file` }) as File;
  const onFileChange = (changedFile, name) => {
    onChangeHandler(changedFile, name);
  };
  return (
    <>
      <label htmlFor={id} className={labelStyle}>
        <CommandBarButton
          styles={{ root: { background: 'transparent', alignSelf: 'stretch', height: '100%' } }}
          iconProps={{ iconName: 'CloudUpload' }}
          text={file ? file.name : 'Upload File'}
          onClick={() => document.getElementById(id).click()}
        />
      </label>
      <Controller
        control={control}
        rules={{ required: true }}
        render={({ fieldState: { invalid }, field: { name } }) => (
          <input type="file" id={id} className={fileStyle} onChange={e => onFileChange(e.target.files[0], name)} required={invalid} />
        )}
        name={`${target}.file`}
      />
    </>
  );
};

const UploadDocSetFile = ({ control, onChangeHandler, name, id, target }) => {
  return (
    <Stack
      horizontal
      verticalAlign="center"
      horizontalAlign="space-between"
      tokens={{ padding: '1rem 0' }}
      styles={{ root: { width: 'fit-content', gap: '2rem' } }}
    >
      <span>{name}</span>
      <Controller
        control={control}
        rules={{ required: true }}
        render={({ fieldState: { invalid }, field: { name: inputName, onBlur, value, ref } }) => (
          <Dropdown
            componentRef={ref}
            errorMessage={invalid ? 'Field is required' : ''}
            onBlur={onBlur}
            selectedKey={value ?? ''}
            onChange={(e, c) => onChangeHandler(c.key, inputName as string)}
            placeholder="Select a Country"
            options={options}
            styles={inputStyle}
          />
        )}
        name={`${target}.country`}
      />
      <UploadFile id={id} onChangeHandler={onChangeHandler} control={control} target={target} />
    </Stack>
  );
};

export const IdentiyDocuments: FunctionComponent<IdentityDocumentsProps> = ({ control, step, onChangeHandler }) => {
  return (
    <>
      <h5 className="subtitle">Identity Documents</h5>
      {step.docSets.map(doc => (
        <UploadDocSetFile
          target={`files.${doc.idDocSetType}.${doc.types}`}
          name={doc.types}
          key={doc.id}
          id={doc.id}
          control={control}
          onChangeHandler={onChangeHandler}
        />
      ))}
    </>
  );
};

export const SelfieDocuments: FunctionComponent<any> = ({ control, onChangeHandler }) => {
  return (
    <>
      <h5 className="subtitle">Selfie Documents</h5>
      <Stack
        horizontal
        verticalAlign="center"
        horizontalAlign="space-between"
        tokens={{ padding: '1rem 0' }}
        styles={{ root: { width: 'fit-content', gap: '2rem' } }}
      >
        <span>Selfie</span>
        <UploadFile
          id="selfie.file"
          control={control}
          onChangeHandler={onChangeHandler}
          target={`files.${IdDocSetType.SELFIE}.${TypeDoc.SELFIE}`}
        />
      </Stack>
    </>
  );
};

export const ProofOfResidenceDocuments: FunctionComponent<ProofOfResidenceDocumentsProps> = ({ onChangeHandler, control }) => {
  return (
    <>
      <h5 className="subtitle">Proof Of Residence Documents</h5>
      <Stack horizontal wrap horizontalAlign="space-between" styles={{ root: { marginTop: '1rem' }, inner: { gap: '3rem' } }}>
        <Controller
          rules={{ required: true }}
          control={control}
          render={({ fieldState: { invalid }, field: { onChange, onBlur, value, name, ref } }) => (
            <TextField
              label="Street"
              errorMessage={invalid ? 'Field is Required' : ''}
              onChange={onChange}
              value={value}
              onBlur={onBlur}
              componentRef={ref}
              styles={inputStyle}
            />
          )}
          name={`${IdDocSetType.PROOF_OF_RESIDENCE}.street`}
        />

        <Controller
          control={control}
          rules={{ required: true }}
          render={({ fieldState: { invalid }, field: { onChange, onBlur, value, name, ref } }) => (
            <TextField
              label="Sub Street"
              errorMessage={invalid ? 'Field is required' : ''}
              onChange={onChange}
              value={value}
              onBlur={onBlur}
              componentRef={ref}
              styles={inputStyle}
            />
          )}
          name={`${IdDocSetType.PROOF_OF_RESIDENCE}.subStreet`}
        />
      </Stack>
      <Stack horizontal wrap horizontalAlign="space-between" styles={{ root: { marginTop: '1rem' }, inner: { gap: '3rem' } }}>
        <Controller
          control={control}
          rules={{ required: true }}
          render={({ fieldState: { invalid }, field: { name, onBlur, value, ref } }) => (
            <Dropdown
              label="Country"
              errorMessage={invalid ? 'Field is required' : ''}
              componentRef={ref}
              onBlur={onBlur}
              selectedKey={value ?? ''}
              onChange={(e, c) => onChangeHandler(c.key, name as string)}
              placeholder="Select a Country"
              options={options}
              styles={inputStyle}
            />
          )}
          name={`${IdDocSetType.PROOF_OF_RESIDENCE}.addresseCountry.name`}
        />
        <Controller
          control={control}
          rules={{ required: true }}
          render={({ fieldState: { invalid }, field: { onBlur, value, name, ref } }) => (
            <Dropdown
              label="State"
              errorMessage={invalid ? 'Field is required' : ''}
              onBlur={onBlur}
              selectedKey={value ?? ''}
              componentRef={ref}
              onChange={(e, c) => onChangeHandler(c.key, name as string)}
              placeholder="Select a City"
              options={options}
              styles={inputStyle}
            />
          )}
          name={`${IdDocSetType.PROOF_OF_RESIDENCE}.state`}
        />
        <Controller
          control={control}
          rules={{ required: true }}
          render={({ fieldState: { invalid }, field: { onBlur, value, name, ref } }) => (
            <Dropdown
              label="Postal/ZIP"
              errorMessage={invalid ? 'Field is required' : ''}
              onBlur={onBlur}
              selectedKey={value ?? ''}
              componentRef={ref}
              onChange={(e, c) => {
                onChangeHandler(c.key, name as string);
              }}
              placeholder="Select ZipCode"
              options={options}
              styles={inputStyle}
            />
          )}
          name={`${IdDocSetType.PROOF_OF_RESIDENCE}.postCode`}
        />
      </Stack>

      <UploadDocSetFile
        id={'address.file'}
        name="Proof Of Residence"
        control={control}
        target={`files.${IdDocSetType.PROOF_OF_RESIDENCE}.${TypeDoc.RESIDENCE_PERMIT}`}
        onChangeHandler={onChangeHandler}
      />
    </>
  );
};

const OtpVerification = ({ control, otpFor, inputTarget, codeTarget }) => {
  return (
    <Stack
      horizontal
      wrap
      verticalAlign="start"
      horizontalAlign="space-between"
      styles={{ root: { marginTop: '1rem', width: 'fit-content' }, inner: { gap: '3rem' } }}
    >
      <Controller
        control={control}
        rules={{ required: true }}
        render={({ fieldState: { invalid }, field: { onChange, onBlur, value, name, ref } }) => (
          <TextField
            errorMessage={invalid ? 'Field is required' : ''}
            label={otpFor}
            onChange={onChange}
            value={value}
            onBlur={onBlur}
            componentRef={ref}
            styles={inputStyle}
          />
        )}
        name={inputTarget}
      />

      <Controller
        control={control}
        rules={{ required: true }}
        render={({ fieldState: { invalid }, field: { onChange, onBlur, value, name, ref } }) => (
          <TextField
            label="Code"
            errorMessage={invalid ? 'Field is required' : ''}
            onChange={onChange}
            value={value}
            onBlur={onBlur}
            componentRef={ref}
            styles={{ root: { width: '25%' } }}
          />
        )}
        name={codeTarget}
      />
      <DefaultButton styles={{ root: { alignSelf: 'end' } }}>Send Code</DefaultButton>
    </Stack>
  );
};

export const PhoneRequiredInfo: FunctionComponent<any> = ({ control }) => {
  return (
    <>
      <h5 className="subtitle">Phone Verification</h5>
      <OtpVerification
        control={control}
        otpFor="Phone Number"
        codeTarget={`${IdDocSetType.PHONE_VERIFICATION}.code`}
        inputTarget={`${IdDocSetType.PHONE_VERIFICATION}.phoneNumber`}
      />
    </>
  );
};

export const EmailRequiredInfo: FunctionComponent<any> = ({ control }) => {
  return (
    <>
      <h5 className="subtitle">Email Verification</h5>
      <OtpVerification
        control={control}
        otpFor="Email"
        codeTarget={`${IdDocSetType.EMAIL_VERIFICATION}.code`}
        inputTarget={`${IdDocSetType.EMAIL_VERIFICATION}.email`}
      />
    </>
  );
};
