# CUSTFMT-N

Formats a customer's name and balance for display on a report or screen.

Joins the first and last name into a single padded field, and renders the balance with a
thousands-separated edit mask so amounts line up in columnar output.

## Parameters

| # | Field | Fmt | I/O | Description |
|---|---|---|---|---|
| 1 | `P-FIRST-NAME` | A20 | in | First name. Optional. |
| 2 | `P-LAST-NAME` | A30 | in | Surname. **Required** — blank returns 4. |
| 3 | `P-BALANCE` | P9.2 | in | Balance to format. |
| 4 | `P-FULL-NAME` | A52 | out | `first last`, space-separated. |
| 5 | `P-AMOUNT-TEXT` | A20 | out | Balance as `ZZZ:ZZZ:ZZ9.99`. |
| 6 | `P-RETURN-CODE` | I2 | out | 0 on success, 4 if surname blank. |

## Return codes

| Code | Meaning |
|---|---|
| 0 | Formatted successfully. |
| 4 | `P-LAST-NAME` was blank; output fields left reset. |

## Usage

```natural
CALLNAT 'CUSTFMT-N'
  CUSTOMER.FIRST-NAME
  CUSTOMER.LAST-NAME
  CUSTOMER.BALANCE
  #FULL-NAME
  #DISPLAY-AMOUNT
  #RETURN-CODE
```

## Notes

- Output fields are reset before any work, so a non-zero return code never leaves stale values.
- The edit mask assumes a maximum of nine significant digits; larger balances truncate.

**See also:** `CUSTRPT` (caller), `CUSTL` (data area)
