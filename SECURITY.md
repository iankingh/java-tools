# Security policy

## Supported versions

Security fixes are applied to the latest 1.x release line. Deprecated migration APIs are supported only until 2.0 and must not be used for new data or new integrations.

## Reporting a vulnerability

Please use GitHub private vulnerability reporting for this repository. Do not open a public issue containing credentials, exploit details, or sensitive payloads.

Include the affected module/version, a minimal reproduction, impact, and any suggested mitigation. You should receive an initial response within seven days.

## Security defaults

- HTTP uses normal JVM certificate and hostname verification.
- XML parsing rejects document types and external entities.
- New encryption uses AES-GCM with a fresh nonce.
- Secrets and complete payloads are not logged by library code.
- CI runs dependency review and CodeQL in addition to tests and SpotBugs.
