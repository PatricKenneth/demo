package br.com.demo.exception.codes;

import lombok.Getter;

public enum ErrorCode {
  // RESOURCE_NOT_FOUND("error.resource.NotFound"),
  // ERROR_ENTRY_DUPLICATED("error.entry.Duplicated"),
  ERROR_UNAUTHORIZED("error.Unauthorized"),
  // ERROR_ACCESS_DENIED("error.AccessDenied"),
  // ERROR_INVALID_REQUEST("error.InvalidRequest"),
  // ERROR_CLIENT_ACTION_ACCESS_DENIED("error.clientAction.AccessDenied"),
  // INVALID_CRM("error.crm.Invalid"),
  // CFM_SERVICE_UNAVAILABLE("error.cfm.ServiceUnavailable"),
  // USER_ALREADY_REGISTERED("error.user.AlreadyRegistered"),
  // USER_ASSIGNED_TO_EXCLUSIVE_COMMUNITY("error.user.AssignedToExclusiveCommunity"),
  // USER_EMAIL_NOT_CONFIRMED("error.user.EmailNotConfirmed"),
  USER_INVALID_CREDENTIALS("error.user.InvalidCredentials"),
  // USER_ALREADY_ASSIGNED("error.user.AlreadyAssigned"),
  // USER_ALREADY_SUBSCRIBED("error.user.AlreadySubscribed"),
  // USER_NOT_SUBSCRIBED("error.user.NotSubscribed"),
  // TRANSMISSION_STATOS_NOT_ALLOWED("error.liveTransmission.StatusNotAllowed"),
  // EVENT_STATUS_NOT_ALLOWED("error.event.StatusNotAllowed"),
  // EVENT_FINISHED("error.event.Finished"),
  // PARTNER_USER_NOT_ALLOWED("error.partner.user.NotAllowed"),
  // USER_INVALID_AUTHORIZATION_CODE("error.user.InvalidAuthorizationCode"),
  // UNPUBLISHABLE_EVENT("error.event.Unpublishable"),
  // USER_WRONG_PASSWORD("error.user.WrongPassword"),
  // CLIENT_REQUIRES_INVITE_CODE("error.client.RequiresInviteCode"),
  // CLIENT_EXCLUSIVE_TO_MEDICS("error.client.ExclusiveToMedics"),
  // UPLOAD_FILE_SIZE_LIMIT_EXCEEDED("error.upload.FileSizeLimitExceeded"),
  // UPLOAD_REQUEST_SIZE_LIMIT_EXCEEDED("error.upload.RequestSizeLimitExceeded"),
  TOKEN_REVOKED("error.token.Revoked"),
  TOKEN_EXPIRED("error.token.Expired");

  @Getter
  private String code;

  ErrorCode(String code) {
    this.code = code;
  }
}
