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
        render={({ field: { name } }) => (
          <input type="file" id={id} className={fileStyle} onChange={e => onFileChange(e.target.files[0], name)} />
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
        render={({ field: { name: inputName, onBlur, value, ref } }) => (
          <Dropdown
            componentRef={ref}
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
          id="address.file"
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
          control={control}
          render={({ field: { onChange, onBlur, value, name, ref } }) => (
            <TextField label="Address" onChange={onChange} value={value} onBlur={onBlur} componentRef={ref} styles={inputStyle} />
          )}
          name="address.address"
        />

        <Controller
          control={control}
          render={({ field: { onChange, onBlur, value, name, ref } }) => (
            <TextField label="Street" onChange={onChange} value={value} onBlur={onBlur} componentRef={ref} styles={inputStyle} />
          )}
          name="address.street"
        />
      </Stack>
      <Stack horizontal wrap horizontalAlign="space-between" styles={{ root: { marginTop: '1rem' }, inner: { gap: '3rem' } }}>
        <Controller
          control={control}
          render={({ field: { name, onBlur, value, ref } }) => (
            <Dropdown
              label="Country"
              componentRef={ref}
              onBlur={onBlur}
              selectedKey={value ?? ''}
              onChange={(e, c) => onChangeHandler(c.key, name as string)}
              placeholder="Select a Country"
              options={options}
              styles={inputStyle}
            />
          )}
          name="address.country"
        />
        <Controller
          control={control}
          render={({ field: { onBlur, value, name, ref } }) => (
            <Dropdown
              label="City"
              onBlur={onBlur}
              selectedKey={value ?? ''}
              componentRef={ref}
              onChange={(e, c) => onChangeHandler(c.key, name as string)}
              placeholder="Select a City"
              options={options}
              styles={inputStyle}
            />
          )}
          name="address.city"
        />
        <Controller
          control={control}
          render={({ field: { onBlur, value, name, ref } }) => (
            <Dropdown
              label="Postal/ZIP"
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
          name="address.zipCode"
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
        render={({ field: { onChange, onBlur, value, name, ref } }) => (
          <TextField label={otpFor} onChange={onChange} value={value} onBlur={onBlur} componentRef={ref} styles={inputStyle} />
        )}
        name={inputTarget}
      />

      <Controller
        control={control}
        render={({ field: { onChange, onBlur, value, name, ref } }) => (
          <TextField
            label="Code"
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
      <OtpVerification control={control} otpFor="Phone Number" codeTarget={'phone.code'} inputTarget={'phone.phoneNumber'} />
    </>
  );
};

export const EmailRequiredInfo: FunctionComponent<any> = ({ control }) => {
  return (
    <>
      <h5 className="subtitle">Email Verification</h5>
      <OtpVerification control={control} otpFor="Email" codeTarget={'email.code'} inputTarget={'email.email'} />
    </>
  );
};
